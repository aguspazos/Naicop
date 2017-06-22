using Entitites;
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

        [Route("api/securityClients/login")]
        [HttpPost]
        public IHttpActionResult login([FromBody]SecurityClient securityClient)
        {
            try
            {
                SecurityClient newSecurityClient = securityClientService.Login(securityClient.Email,securityClient.Password);
                if (newSecurityClient != null)
                    return Ok(new JsonResponse<SecurityClient>("Ok", newSecurityClient));

                return Ok(new JsonResponse<string>("Error","No existe el usuario"));
            }catch(SecurityClientException ex)
            {
                return Ok(new JsonResponse<string>("Error", ex.Message));
            }

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