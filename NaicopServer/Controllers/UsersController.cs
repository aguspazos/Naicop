using Entitites;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace NaicopServer.Controllers
{
    public class UsersController : ApiController
    {

        private UserService userService;
        public UsersController()
        {
            userService = new UserService();
        }
        // GET api/<controller>
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }


        [Route("api/users/login")]
        [HttpGet]
        public IHttpActionResult Login(string email,string password)
        {
            User user = userService.Login(email, password);
            if (user == null)
                return NotFound();
            else
                return Ok(user);
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