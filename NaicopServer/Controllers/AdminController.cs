using Entitites;
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
    public class AdminController : ApiController
    {
        private AdminService adminService;
        private IAuthenticationService authenticationService;
        public AdminController()
        {
            adminService = new AdminService();
            authenticationService = new AuthenticationService();
        }
        public AdminController(AdminService adminService)
        {
            this.adminService = adminService;
            this.authenticationService = new AuthenticationService();
        }

        [Route("api/admins/Login")]
        [HttpPost]
        public IHttpActionResult login(Admin adminToLog)
        {


            Admin admin = adminService.Login(adminToLog.Email, adminToLog.Password);
            if (admin == null)
            {
                return NotFound();
            }
            else
            {
                authenticationService.Log(admin);
                return Ok(admin);
            }
        }

        [Route("api/admins")]
        [HttpPost]
        public IHttpActionResult Post(Admin admin)
        {
            admin.Role = Roles.ADMIN;
            Admin newAdmin = adminService.CreateAdmin(admin);
            if (authenticationService.CheckPermission(Roles.ADMIN))
            {

                if (newAdmin == null)
                    return NotFound();
                else
                    return Ok(admin);
            }
            else {
                return Ok("CONCAs");
            }

        }



        // GET: api/Admin
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/Admin/5
        public string Get(int id)
        {
            return "value";
        }

        // POST: api/Admin
        public void Post([FromBody]string value)
        {
        }

        // PUT: api/Admin/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Admin/5
        public void Delete(int id)
        {
        }
    }
}
