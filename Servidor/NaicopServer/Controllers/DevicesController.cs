using Entitites;
using NaicopServer.Dtos;
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
    public class DevicesController : ApiController
    {
        private IEventService eventService;
        private ICategoryService categoryService;

        public DevicesController() { 
        
            eventService = new EventService();
            categoryService = new CategoryService();
        }
        // GET api/<controller>
        [Route("api/devices/getUpdated")]
        [HttpPost]
        public IHttpActionResult GetUpdated(JObject jsonObject) 
        {
            string token = jsonObject["token"].Value<string>();
            DateTime lastUpdated = jsonObject["lastUpdated"].Value<DateTime>();

            UpdateDeviceDto data = new UpdateDeviceDto();
            data.Events = eventService.GetAll();
            data.Categories = categoryService.GetAll();
            return Ok(data);
        }

        // GET api/<controller>/5
        public string Get(int id)
        {
            return "value";
        }

    }
}