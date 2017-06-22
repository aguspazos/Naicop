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
    public class UserControllerTest
    {
        [Fact]
        public void CheckTokenTest()
        {
            User user = new User();
            var mockUserService = new Mock<IUserService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockUserService.Setup(x => x.GetFromToken(It.IsAny<string>())).Returns(user);

            var controller = new UsersController(mockUserService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.CheckToken("token");
            OkNegotiatedContentResult<JsonResponse<string>> contentResult = Assert.IsType<OkNegotiatedContentResult<JsonResponse<string>>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content.Status, "Ok");
        }

        [Fact]
        public void CheckTokenNotFoundTest()
        {
            User user = null;
            var mockUserService = new Mock<IUserService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockUserService.Setup(x => x.GetFromToken(It.IsAny<string>())).Returns(user);

            var controller = new UsersController(mockUserService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.CheckToken("token");
            NotFoundResult contentResult = Assert.IsType<NotFoundResult>(actionResult);
            Assert.NotNull(contentResult);
        }

        [Fact]
        public void LoginTest()
        {
            User user = new User();
            var mockUserService = new Mock<IUserService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockUserService.Setup(x => x.Login(It.IsAny<string>(), It.IsAny<string>())).Returns(user);

            var controller = new UsersController(mockUserService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.Login(user);
            OkNegotiatedContentResult<User> contentResult = Assert.IsType<OkNegotiatedContentResult<User>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content, user);
        }

        [Fact]
        public void LoginFailedTest()
        {
            User userToLog = new User();
            User user = null;
            var mockUserService = new Mock<IUserService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockUserService.Setup(x => x.Login(It.IsAny<string>(), It.IsAny<string>())).Returns(user);

            var controller = new UsersController(mockUserService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.Login(userToLog);
            NotFoundResult contentResult = Assert.IsType<NotFoundResult>(actionResult);
            Assert.NotNull(contentResult);
        }

        [Fact]
        public void PostTest()
        {
            User user = new User();
            var mockUserService = new Mock<IUserService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockUserService.Setup(x => x.CreateUser(user)).Returns(user);

            var controller = new UsersController(mockUserService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.Post(user);
            OkNegotiatedContentResult<JsonResponse<User>> contentResult = Assert.IsType<OkNegotiatedContentResult<JsonResponse<User>>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content.Status, "Ok");
            Assert.Equal(contentResult.Content.Entity, user);
        }

        [Fact]
        public void PostFailTest()
        {
            UserException userException = new UserException("Mensaje Excepcion");
            User user = new User();
            var mockUserService = new Mock<IUserService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockUserService.Setup(x => x.CreateUser(user)).Throws(userException);

            var controller = new UsersController(mockUserService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.Post(user);
            OkNegotiatedContentResult<JsonResponse<string>> contentResult = Assert.IsType<OkNegotiatedContentResult<JsonResponse<string>>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content.Status, "Error");
            Assert.Equal(contentResult.Content.Entity, userException.Message);
        }

        [Fact]
        public void FacebookLoginTest()
        {
            User user = new User();
            var mockUserService = new Mock<IUserService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockUserService.Setup(x => x.FacebookLogin(user)).Returns(user);

            var controller = new UsersController(mockUserService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.FacebookLogin(user);
            OkNegotiatedContentResult<JsonResponse<User>> contentResult = Assert.IsType<OkNegotiatedContentResult<JsonResponse<User>>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content.Status, "Ok");
            Assert.Equal(contentResult.Content.Entity, user);
        }

        [Fact]
        public void FacebookLoginFailTest()
        {
            UserException userException = new UserException("Mensaje Excepcion");
            User user = new User();
            var mockUserService = new Mock<IUserService>();
            var mockAuthenticationService = new Mock<IAuthenticationService>();

            mockUserService.Setup(x => x.FacebookLogin(user)).Throws(userException);

            var controller = new UsersController(mockUserService.Object, mockAuthenticationService.Object);

            IHttpActionResult actionResult = controller.FacebookLogin(user);
            OkNegotiatedContentResult<JsonResponse<string>> contentResult = Assert.IsType<OkNegotiatedContentResult<JsonResponse<string>>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content.Status, "Error");
            Assert.Equal(contentResult.Content.Entity, userException.Message);
        }
    }
}
