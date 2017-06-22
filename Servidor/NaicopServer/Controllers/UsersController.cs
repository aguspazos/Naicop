using Entitites;
using Entitites.Exceptions;
using NaicopServer.Dtos;
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

        private IUserService userService;
        private IAuthenticationService authenticationService;

        public UsersController()
        {
            userService = new UserService();
            authenticationService = new Services.Implementations.AuthenticationService();
        }

        public UsersController(IUserService userService, IAuthenticationService authenticationService)
        {
            this.userService = userService;
            this.authenticationService = authenticationService; 
        }
        // GET api/<controller>
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }


        [Route("api/users/checkToken/{token}")]
        [HttpGet]
        public IHttpActionResult CheckToken([FromUri]string token)
        {
            User user = userService.GetFromToken(token);
            if (user == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(new JsonResponse<string>()
                {
                    Status = "Ok",
                    Entity = token
                });
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
            try
            {
                user.Role = Roles.USER;
                User newUser = userService.CreateUser(user);
                return Ok(new JsonResponse<User>("Ok", user));
            }catch(UserException ex)
            {
                return Ok(new JsonResponse<string>("Error",ex.Message));
            }

        }
        [Route("api/users/facebookLogin")]
        [HttpPost]
        public IHttpActionResult FacebookLogin([FromBody]User user)
        {
            user.Role = Roles.USER;
            try
            {
                User newUser = userService.FacebookLogin(user);
                return Ok(new JsonResponse<User>("Ok",user));
            }catch(UserException ex)
            {
                return Ok(new JsonResponse<string>("Error", ex.Message));
            }
        }

    }
}