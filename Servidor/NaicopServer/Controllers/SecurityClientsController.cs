using Entitites;
using Services;
using Services.Implementations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace NaicopServer.Controllers
{
    public class SecurityClientsController : ApiController
    {
        private ISecurityClientService securityClientService;

        public SecurityClientsController()
        {
            this.securityClientService = new SecurityClientService();
        }

        // GET api/<controller>/id
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<controller>
        [Route("api/securityClients")]
        [HttpPost]
        public IHttpActionResult Post([FromBody]SecurityClient securityClient)
        {
            SecurityClient newSecurityClient = securityClientService.CreateSecurityClient(securityClient);
            return Ok(newSecurityClient);

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