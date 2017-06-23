using Entitites;
using Services;
using Services.Implementations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace NaicopServer.Controllers
{
    public class CategoriesController : ApiController
    {

        private ICategoryService categoryService;

        public CategoriesController()
        {
            this.categoryService = new CategoryService();
        }

        public CategoriesController(ICategoryService categoryService)
        {
            this.categoryService = categoryService;
        }

        // POST api/<controller>
        [Route("api/categories")]
        [HttpPost]
        public IHttpActionResult Post([FromBody]Category category)
        {
            Category newCategory = categoryService.CreateCategory(category);
            return Ok(category);

        }
        [Route("api/categories")]
        [HttpGet]
        public IHttpActionResult Get()
        {
            List<Category> categories = categoryService.GetAll();
            return Ok(categories);

        }
        [Route("api/categories/delete")]
        [HttpPost]
        public IHttpActionResult Delete([FromBody]Category category)
        {
            bool deleted = categoryService.Delete(category);
            if (deleted)
                return Ok();
            else
                return NotFound();
        }

    }
}