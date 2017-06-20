﻿using System;
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

        public List<Category> GetAll()
        {
            return unitOfWork.CategoryRepository.Get().ToList();
        }
    }
}