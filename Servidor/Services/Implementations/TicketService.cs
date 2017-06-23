using System;
using Entitites;
using Repository;
using System.Linq;
using Utils;
using System.IO;
using System.Drawing;
using System.Collections.Generic;
using ZXing;
using Entitites.Exceptions;

namespace Services.Implementations
{
    public class TicketService : ITicketService
    {

        private IUnitOfWork unitOfWork;
        
        public TicketService()
        {
            this.unitOfWork = new UnitOfWork();
        }
        
        public bool Pay(Ticket ticket)
        {
            Ticket myTicket = unitOfWork.TicketRepository.GetByID(ticket.ID);
            if (myTicket != null)
            {
                myTicket.Status = TicketStatus.OK;
                unitOfWork.TicketRepository.Update(myTicket);
                unitOfWork.Save();
                return true;
            }
            return false;

        }
        public Ticket CreateTicket(Ticket ticket)
        {
            validateTicket(ticket);



            string code = HelperFunctions.RandomString(20);
            ticket.Code = code;
            ticket.Status = TicketStatus.WAITING;
            ticket.QrImageUrl = createQrCode(code);
            ticket.UpdatedOn = DateTime.Now;
            ticket.CreatedOn = DateTime.Now;
            ticket.Deleted = 0;
            ticket.Used = 0;
            unitOfWork.TicketRepository.Insert(ticket);
            unitOfWork.Save();
            return ticket;
        }

        private void validateTicket(Ticket ticket)
        {
            Event ticketEvent = unitOfWork.EventRepository.GetByID(ticket.EventID);
            User user = unitOfWork.UserRepository.GetByID(ticket.UserID);

            if (ticketEvent == null)
                throw new TicketException("Error al crear el ticket");
            if (user == null)
                throw new TicketException("Error al crear el ticket");
        }
        private string createQrCode(string code)
        {
            BarcodeWriter writer = new BarcodeWriter
            { Format = BarcodeFormat.QR_CODE };
            var result = writer.Write(code);
            var barcodeBitmap = new Bitmap(result);
            
            System.IO.MemoryStream stream = new System.IO.MemoryStream();
            barcodeBitmap.Save(stream, System.Drawing.Imaging.ImageFormat.Bmp);
            byte[] imageBytes = stream.ToArray();
            string base64String = Convert.ToBase64String(imageBytes);
            return ImageHelper.saveImage(base64String, "qr.jpg");
        }

        public List<Ticket> GetUpdated(DateTime updatedOn)
        {
            return unitOfWork.TicketRepository.Get(t => t.UpdatedOn >= updatedOn).ToList();
        }
        public Ticket GetByCode(string code)
        {
            var ticketsEnumerable = unitOfWork.TicketRepository.Get(t => t.Code == code);
            if (ticketsEnumerable.Count() > 0)
            {
                return ticketsEnumerable.First();
            }
            else
            {
                return null;
            }

        }

        public Ticket ScanTicket(Ticket ticket)
        {
            ticket.Used = 1;
            ticket.UpdatedOn = DateTime.Now;
            unitOfWork.TicketRepository.Update(ticket);
            unitOfWork.Save();
            return ticket;
        }

        public Ticket MakePayment(Ticket ticket)
        {
            ticket = unitOfWork.TicketRepository.GetByID(ticket.ID);
            validateTicket(ticket);
            ticket.Status = TicketStatus.OK;
            unitOfWork.TicketRepository.Update(ticket);
            unitOfWork.Save();
            return ticket;
        }
    }
}
