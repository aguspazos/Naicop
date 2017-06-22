using NaicopServer.Controllers;
using Moq;
using Xunit;
using System.Web.Http;
using NaicopServer.Dtos;
using System.Web.Http.Results;
using Services;
using Entitites.Exceptions;
using Entitites;

namespace Web.Api.Test
{
    public class CategoriesControllerTest
    {
        [Fact]
        public void PostTest()
        {
            Category category = new Category();
            var mockCategoryService = new Mock<ICategoryService>();

            mockCategoryService.Setup(x => x.CreateCategory(category)).Returns(category);

            var controller = new CategoriesController(mockCategoryService.Object);

            IHttpActionResult actionResult = controller.Post(category);
            OkNegotiatedContentResult<Category> contentResult = Assert.IsType<OkNegotiatedContentResult<Category>>(actionResult);
            Assert.NotNull(contentResult);
            Assert.NotNull(contentResult.Content);
            Assert.Equal(contentResult.Content, category);
        }
    }
}
