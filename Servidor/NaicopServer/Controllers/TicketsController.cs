using Entitites;
using Newtonsoft.Json.Linq;
using Services;
using Services.Implementations;
using NaicopServer.Dtos;
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
        private ISecurityClientService securityClientService;

        public TicketsController()
        {
            this.ticketService = new TicketService();
            this.securityClientService = new SecurityClientService();
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

        // POST api/<controller>
        [Route("api/tickets")]
        [HttpPost]
        public IHttpActionResult Post([FromBody]Ticket ticket)
        {
            Ticket newTicket = ticketService.CreateTicket(ticket);
            return Ok(newTicket);

        }

        // POST api/<controller>
        [Route("api/tickets/validateQrCode")]
        [HttpPost]
        public IHttpActionResult ValidateQrCode([FromBody] string ticketCode)
        {
            Ticket ticket = ticketService.GetByCode(ticketCode);
            if (ticket == null)
            {
                return Ok(new JsonResponse<string>()
                {
                    Status = "Error",
                    Entity = "Acceso denegado"
                });
            }
            else
            {
                if (ticket.Status == TicketStatus.OK)
                {
                    return Ok(new JsonResponse<string>()
                    {
                        Status = "Ok",
                        Entity = "Acceso garantizado"
                    });

                }
                else
                {
                    return Ok(new JsonResponse<string>()
                    {
                        Status = "Error",
                        Entity = "Acceso denegado"
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