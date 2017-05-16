﻿using Entitites;
using Services;
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
        public AdminController()
        {
            adminService = new AdminService();
        }
        public AdminController(AdminService adminService)
        {
            this.adminService = adminService;
        }

        [Route("api/admins/login")]
        [HttpGet]
        public IHttpActionResult login(string email,string password)
        {
            Admin admin = adminService.Login(email, password);
            if (admin == null)
                return NotFound();
            else
                return Ok(admin);
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
