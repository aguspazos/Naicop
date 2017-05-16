using Entitites;
using Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public class AdminService
    {
        public Admin Login(string email,string password)
        {
            Admin admin = new Admin();//ir a buscarlo;
            if(admin != null)
            {
                SessionHelper.SetCurrentAuthenticated(admin);
                return admin;
            }
            return null;

        }
    }
}
