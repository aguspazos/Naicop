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
    public class AdminControllerTest
    {
        [Fact]
        public void LoginTest()
        {
            Admin admin = new Admin();
            var mockAdminService = new Mock<IAdminService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockAdminService.Setup(x => x.Login(It.IsAny<string>(), It.IsAny<string>())).Returns(admin);

            var controller = new AdminController(mockAdminService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.login(admin);
            OkNegotiatedContentResult<Admin> contentResult = Assert.IsType<OkNegotiatedContentResult<Admin>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content, admin);
        }

        [Fact]
        public void LoginFailTest()
        {
            Admin a_admin = null;
            Admin admin = new Admin();
            var mockAdminService = new Mock<IAdminService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockAdminService.Setup(x => x.Login(It.IsAny<string>(), It.IsAny<string>())).Returns(a_admin);

            var controller = new AdminController(mockAdminService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.login(admin);
            NotFoundResult contentResult = Assert.IsType<NotFoundResult>(actionResult);
            Assert.NotNull(contentResult);
        }

        [Fact]
        public void PostTest()
        {
            Admin admin = new Admin();
            var mockAdminService = new Mock<IAdminService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockAdminService.Setup(x => x.CreateAdmin(It.IsAny<Admin>())).Returns(admin);
            mockAuthenticationService.Setup(x => x.CheckPermission(It.IsAny<int>(), false)).Returns(true);

            var controller = new AdminController(mockAdminService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.Post(admin);
            OkNegotiatedContentResult<Admin> contentResult = Assert.IsType<OkNegotiatedContentResult<Admin>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content, admin);
        }

        [Fact]
        public void PostFailTest()
        {
            Admin a_admin = null;
            Admin admin = new Admin();
            var mockAdminService = new Mock<IAdminService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockAdminService.Setup(x => x.CreateAdmin(It.IsAny<Admin>())).Returns(a_admin);
            mockAuthenticationService.Setup(x => x.CheckPermission(It.IsAny<int>(), false)).Returns(true);

            var controller = new AdminController(mockAdminService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.Post(admin);
            NotFoundResult contentResult = Assert.IsType<NotFoundResult>(actionResult);
            Assert.NotNull(contentResult);
        }
    }
}
