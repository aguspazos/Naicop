using Entitites;
using Entitites.Exceptions;
using Newtonsoft.Json.Linq;
using Services;
using Services.Implementations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace NaicopServer.Controllers
{
    public class ClientUsersController : ApiController
    {
        private IClientUserService clientUserService;
        private IAuthenticationService authenticationService;

        public ClientUsersController()
        {
            clientUserService = new ClientUserService();
            authenticationService = new Services.Implementations.AuthenticationService();
        }

        public ClientUsersController(IClientUserService clientUserService, IAuthenticationService authenticationService)
        {
            this.clientUserService = clientUserService;
            this.authenticationService = authenticationService;
        }



        [Route("api/clientUsers")]
        [HttpPost]
        public IHttpActionResult Post(JObject jsonClient)
        {
            ClientUser newClientUser = new ClientUser()
            {
                Email = jsonClient["email"].Value<string>(),
                Password = jsonClient["password"].Value<string>(),
                Description = jsonClient["description"].Value<string>(),
            };

            string image = jsonClient["image"].Value<string>();
            string imageName = jsonClient["image_name"].Value<string>();
            try
            {
                ClientUser myClientUser = clientUserService.CreateClientUser(newClientUser, image, imageName);
                if (myClientUser == null)
                {
                    return NotFound();
                }
                else
                {
                    return Ok(myClientUser);
                }
            }
            catch (ClientUserException ex)
            {
                return BadRequest(ex.Message);
            }

        }

        [Route("api/clientUsers/Login")]
        [HttpPost]
        public IHttpActionResult Login(ClientUser clientUserToLog)
        {
            ClientUser clientUser = clientUserService.Login(clientUserToLog.Email, clientUserToLog.Password);
            if (clientUser == null)
            {
                return NotFound();
            }
            else
            {
                authenticationService.Log(clientUser);
                return Ok(clientUser);
            }
        }


    }
}