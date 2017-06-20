using Entitites;
using Helpers;
using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services.Implementations
{
    public class AdminService : IAdminService
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
                Admin admin = admins.Last();

                return admin;
            }
            return null;

        }

        public Admin CreateAdmin(Admin admin)
        {
            unitOfWork.AdminRepository.Insert(admin);
            unitOfWork.Save();
            return admin;
        }
    }
}
