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
        [Route("api/tickets")]
        [HttpPost]
        public IHttpActionResult CheckTicketFromQrCode(JObject jsonObject)
        {
            JsonResponse<string> response = new JsonResponse<string>();
            var securityClientToken = jsonObject["token"].Value<string>();
            var ticketCode = jsonObject["code"].Value<string>();
            var securityClient = securityClientService.GetByToken(securityClientToken);
            if(securityClient != null)
            {
                Ticket ticket = ticketService.GetByCode(ticketCode);
                //if(ticket)
                return Ok(ticket);
            }
            else
            {
                response.status = "Error";
                response.Entity = "Permiso de escaneo denegado";
                return Ok(response);
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