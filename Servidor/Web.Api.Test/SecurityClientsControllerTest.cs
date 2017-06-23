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
    public class SecurityClientsControllerTest
    {
        [Fact]
        public void PostTest()
        {
            SecurityClient securityClient = new SecurityClient();
            var mockSecurityClientService = new Mock<ISecurityClientService>();
            mockSecurityClientService.Setup(x => x.CreateSecurityClient(It.IsAny<SecurityClient>())).Returns(securityClient);
            
            var controller = new SecurityClientsController(mockSecurityClientService.Object);

            IHttpActionResult actionResult = controller.Post(securityClient);
            OkNegotiatedContentResult<SecurityClient> contentResult = Assert.IsType<OkNegotiatedContentResult<SecurityClient>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content, securityClient);
        }

        [Fact]
        public void LoginTest()
        {
            SecurityClient securityClient = new SecurityClient();
            var mockSecurityClientService = new Mock<ISecurityClientService>();
            mockSecurityClientService.Setup(x => x.Login(It.IsAny<string>(), It.IsAny<string>())).Returns(securityClient);

            var controller = new SecurityClientsController(mockSecurityClientService.Object);

            IHttpActionResult actionResult = controller.login("email","pass");
            OkNegotiatedContentResult<SecurityClient> contentResult = Assert.IsType<OkNegotiatedContentResult<SecurityClient>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content, securityClient);
        }

    }
}
