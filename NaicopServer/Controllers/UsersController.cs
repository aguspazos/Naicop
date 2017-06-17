using Entitites;
using Services;
using Services.Implementations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.ApplicationServices;
using System.Web.Http;

namespace NaicopServer.Controllers
{
    public class UsersController : ApiController
    {

        private UserService userService;
        private IAuthenticationService authenticationService;

        public UsersController()
        {
            userService = new UserService();
            authenticationService = new Services.Implementations.AuthenticationService();
        }
        // GET api/<controller>
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }


        [Route("api/users/checkToken/{token}")]
        [HttpGet]
        public IHttpActionResult checkToken(string token)
        {
            User user = userService.GetFromToken(token);
            if (user == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(token);
            }
        }
        [Route("api/users/Login")]
        [HttpPost]
        public IHttpActionResult Login(User userToLog)
        {
            User user = userService.Login(userToLog.Email, userToLog.Password);
            if (user == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(user);
            }
        }
        // GET api/<controller>/5
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<controller>
        [Route("api/users")]
        [HttpPost]
        public IHttpActionResult Post([FromBody]User user)
        {
            user.Role = Roles.USER;
            User newUser = userService.CreateUser(user);
            return Ok(user);

        }

        // PUT api/<controller>/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/<controller>/5
        public void Delete(int id)
        {
        }
    }
}