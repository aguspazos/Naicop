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