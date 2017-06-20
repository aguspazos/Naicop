using Entitites;
using Newtonsoft.Json.Linq;
using Services;
using Services.Implementations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace NaicopServer
{
    public class EventsController : ApiController
    {


        private IEventService eventService;
        private IAuthenticationService authenticationService;

        public EventsController()
        {
            eventService = new EventService();
            authenticationService = new Services.Implementations.AuthenticationService();
        }



        [Route("api/events/")]
        [HttpPost]
        public IHttpActionResult Post(JObject jsonEvent)
        {
            Event newEvent = new Event()
            {
                Title = jsonEvent["title"].Value<string>(),
                Description = jsonEvent["description"].Value<string>(),
                Latitude = jsonEvent["latitude"].Value<string>(),
                Longitude = jsonEvent["longitude"].Value<string>(),
                MaxCapacity = jsonEvent["max_capacity"].Value<int>(),
                ClientUserID = jsonEvent["client_user_id"].Value<int>(),
                CategoryID = jsonEvent["category_id"].Value<int>(),
                Price = jsonEvent["price"].Value<double>(),
                StartDate = jsonEvent["start_date"].Value<DateTime>(),
                EndDate = jsonEvent["end_date"].Value<DateTime>()
            };

            string image = jsonEvent["image"].Value<string>() ;
            string imageName = jsonEvent["image_name"].Value<string>();
            Event myEvent = eventService.CreateEvent(newEvent,image,imageName);
            if (myEvent == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(myEvent);
            }
        }
    }
}