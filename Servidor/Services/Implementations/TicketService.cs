using System;
using Entitites;
using Repository;
using Utils;
using System.IO;
using System.Drawing;
using ZXing;
using System.Collections.Generic;

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
            string code = HelperFunctions.RandomString(20);
            ticket.Status = TicketStatus.WAITING;
            ticket.QrImageUrl = createQrCode(code);
            unitOfWork.TicketRepository.Insert(ticket);
            unitOfWork.Save();
            return ticket;
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
    }
}
