using Entitites;
using Helpers;
using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public class AdminService
    {

        private IUnitOfWork unitOfWork;

        public AdminService()
        {
            this.unitOfWork = new UnitOfWork();
        }
        public AdminService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }
        public Admin Login(string email,string password)
        {
            IEnumerable<Admin> admins = unitOfWork.AdminRepository.Get((u => u.Email == email));

            if (admins.Count() > 0)
            {
                Admin admin = admins.First();

                return admin;
            }
            return null;

        }
    }
}
