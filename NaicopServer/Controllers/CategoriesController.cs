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

        // GET api/<controller>/id
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<controller>
        [Route("api/categories")]
        [HttpPost]
        public IHttpActionResult Post([FromBody]Category category)
        {
            Category newCategory = categoryService.CreateCategory(category);
            return Ok(category);

        }

        // PUT api/<controller>/id
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/<controller>/id
        public void Delete(int id)
        {
        }
    }
}