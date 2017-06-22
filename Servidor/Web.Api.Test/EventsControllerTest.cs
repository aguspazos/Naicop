using Entitites;
using NaicopServer.Controllers;
using Moq;
using Xunit;
using System.Web.Http;
using NaicopServer.Dtos;
using System.Web.Http.Results;
using Services;
using System;
using Entitites.Exceptions;
using Newtonsoft.Json.Linq;
using NaicopServer;

namespace Web.Api.Test
{
    public class EventsControllerTest
    {
        [Fact]
        public void PostTest()
        {
            JObject jsonEvent = new JObject();
            jsonEvent["title"] = "";
            jsonEvent["description"] = "";
            jsonEvent["latitude"] = "";
            jsonEvent["longitude"] = "";
            jsonEvent["max_capacity"] = 1;
            jsonEvent["client_user_id"] = 1;
            jsonEvent["category_id"] = 1;
            jsonEvent["price"] = 1;
            jsonEvent["start_date"] = new DateTime();
            jsonEvent["end_date"] = new DateTime();
            jsonEvent["image"] = "";
            jsonEvent["image_name"] = "";
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
            var mockEventService = new Mock<IEventService>();

            mockEventService.Setup(x => x.CreateEvent(It.IsAny<Event>(), It.IsAny<string>(), It.IsAny<string>() )).Returns(newEvent);

            var controller = new EventsController(mockEventService.Object);

            IHttpActionResult actionResult = controller.Post(jsonEvent);
            OkNegotiatedContentResult<Event> contentResult = Assert.IsType<OkNegotiatedContentResult<Event>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
        }


        [Fact]
        public void PostFailedTest()
        {
            JObject jsonEvent = new JObject();
            jsonEvent["title"] = "";
            jsonEvent["description"] = "";
            jsonEvent["latitude"] = "";
            jsonEvent["longitude"] = "";
            jsonEvent["max_capacity"] = 1;
            jsonEvent["client_user_id"] = 1;
            jsonEvent["category_id"] = 1;
            jsonEvent["price"] = 1;
            jsonEvent["start_date"] = new DateTime();
            jsonEvent["end_date"] = new DateTime();
            jsonEvent["image"] = "";
            jsonEvent["image_name"] = "";
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
            Event a_event = null;
            var mockEventService = new Mock<IEventService>();

            mockEventService.Setup(x => x.CreateEvent(It.IsAny<Event>(), It.IsAny<string>(), It.IsAny<string>())).Returns(a_event);

            var controller = new EventsController(mockEventService.Object);

            IHttpActionResult actionResult = controller.Post(jsonEvent);
            NotFoundResult contentResult = Assert.IsType<NotFoundResult>(actionResult);
            Assert.NotNull(contentResult);
        }
    }
}
