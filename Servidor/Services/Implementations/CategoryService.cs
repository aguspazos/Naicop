using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Entitites;
using Repository;

namespace Services.Implementations
{
    public class CategoryService : ICategoryService
    {

        private IUnitOfWork unitOfWork;

        public CategoryService()
        {
            this.unitOfWork = new UnitOfWork();
        }

        public CategoryService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public Category CreateCategory(Category category)
        {
            category.CreatedOn = DateTime.Now;
            category.UpdatedOn = DateTime.Now;
            category.Deleted = 0;
            unitOfWork.CategoryRepository.Insert(category);
            unitOfWork.Save();
            return category;
        }

        public bool Delete(Category category)
        {
            Category myCategory = unitOfWork.CategoryRepository.GetByID(category.ID);
            if(myCategory != null)
            {
                myCategory.Deleted = 1;
                myCategory.UpdatedOn = DateTime.Now;
                unitOfWork.CategoryRepository.Update(category);
                unitOfWork.Save();
                return true;
            }
            return false;
                
                
        }

        public List<Category> GetAll()
        {
            return unitOfWork.CategoryRepository.Get().ToList();
        }
    }
}
