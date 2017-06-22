using Entitites;
using NaicopServer.Controllers;
using Moq;
using Xunit;
using System.Web.Http;
using NaicopServer.Dtos;
using System.Web.Http.Results;
using Services;
using Entitites.Exceptions;

namespace Web.Api.Test
{
    public class TicketsControllerTest
    {
        [Fact]
        public void PostTest()
        {
            Ticket ticket = new Ticket();
            var mockTicketService = new Mock<ITicketService>();

            mockTicketService.Setup(x => x.CreateTicket(ticket)).Returns(ticket);

            var controller = new TicketsController(mockTicketService.Object);

            IHttpActionResult actionResult = controller.Post(ticket);
            OkNegotiatedContentResult<Ticket> contentResult = Assert.IsType<OkNegotiatedContentResult<Ticket>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content, ticket);
        }

        [Fact]
        public void ValidateQrCodeTest()
        {
            Ticket ticket = new Ticket();
            ticket.Status = TicketStatus.OK;
            var mockTicketService = new Mock<ITicketService>();

            mockTicketService.Setup(x => x.GetByCode(It.IsAny<string>())).Returns(ticket);

            var controller = new TicketsController(mockTicketService.Object);

            IHttpActionResult actionResult = controller.ValidateQrCode(It.IsAny<string>());
            OkNegotiatedContentResult<JsonResponse<string>> contentResult = Assert.IsType<OkNegotiatedContentResult<JsonResponse<string>>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content.Status, "Ok");
            Assert.Equal(contentResult.Content.Entity, "Acceso garantizado");
        }

        [Fact]
        public void ValidateQrCodeTicketNotFoundFailTest()
        {
            Ticket ticket = null;
            var mockTicketService = new Mock<ITicketService>();

            mockTicketService.Setup(x => x.GetByCode(It.IsAny<string>())).Returns(ticket);

            var controller = new TicketsController(mockTicketService.Object);

            IHttpActionResult actionResult = controller.ValidateQrCode(It.IsAny<string>());
            OkNegotiatedContentResult<JsonResponse<string>> contentResult = Assert.IsType<OkNegotiatedContentResult<JsonResponse<string>>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content.Status, "Error");
            Assert.Equal(contentResult.Content.Entity, "Acceso denegado");
        }

        [Fact]
        public void ValidateQrCodeTicketStatusNotOkFailTest()
        {
            Ticket ticket = new Ticket();
            ticket.Status = TicketStatus.WAITING;
            var mockTicketService = new Mock<ITicketService>();

            mockTicketService.Setup(x => x.GetByCode(It.IsAny<string>())).Returns(ticket);

            var controller = new TicketsController(mockTicketService.Object);

            IHttpActionResult actionResult = controller.ValidateQrCode(It.IsAny<string>());
            OkNegotiatedContentResult<JsonResponse<string>> contentResult = Assert.IsType<OkNegotiatedContentResult<JsonResponse<string>>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content.Status, "Error");
            Assert.Equal(contentResult.Content.Entity, "Acceso denegado");
        }
    }
}
