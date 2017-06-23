using Entitites;
using Entitites.Exceptions;
using NaicopServer.Dtos;
using Services;
using Services.Implementations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace NaicopServer.Controllers
{
    public class TicketsController : ApiController
    {
        private ITicketService ticketService;

        public TicketsController()
        {
            this.ticketService = new TicketService();
        }

        public TicketsController(ITicketService ticketService)
        {
            this.ticketService = ticketService;
        }

        // GET api/<controller>/id
        public string Get(int id)
        {
            return "value";
        }

        [Route("api/tickets/makePayment")]
        [HttpPost]
        public IHttpActionResult makePayment([FromBody]Ticket ticket)
        {
            try
            {
                Ticket newTicket = ticketService.MakePayment(ticket);
                return Ok(new JsonResponse<Ticket>("Ok", newTicket));

            }
            catch (TicketException ex)
            {
                return Ok(new JsonResponse<string>("Error", ex.Message));
            }

        }
        // POST api/<controller>
        [Route("api/tickets")]
        [HttpPost]
        public IHttpActionResult Post([FromBody]Ticket ticket)
        {
            try
            {
                Ticket newTicket = ticketService.CreateTicket(ticket);
                return Ok(new JsonResponse<Ticket>("Ok",newTicket));

            }catch(TicketException ex)
            {
                return Ok(new JsonResponse<string>("Error", ex.Message));
            }

        }
        [Route("api/tickets/validateQrCode")]
        [HttpPost]
        public IHttpActionResult ValidateQrCode([FromBody]Ticket ticket)
        {
            Ticket existingTicket = ticketService.GetByCode(ticket.Code);
            if (existingTicket == null)
            {
                ticketService.ScanTicket(existingTicket);
                return Ok(new JsonResponse<string>()
                {
                    Status = "Error",
                    Entity = "Acceso denegado"
                });
            }
            else
            {
                if (existingTicket.Status == TicketStatus.OK)
                {
                    return Ok(new JsonResponse<string>()
                    {
                        Status = "Ok",
                        Entity = "Acceso autorizado"
                    });
                }
                else
                {
                    return Ok(new JsonResponse<string>()
                    {
                        Status = "Error",
                        Entity = "No se realizo el pago del ticket"
                    });
                }
            }


        }

        // PUT api/<controller>/id
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/<controller>/id
        public void Delete(int id)
        {
        }
    }
}