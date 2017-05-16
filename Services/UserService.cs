using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public class UserService
    {
        public User Login(string email,string password)
        {
            User user = new User(); // buscarlo en bd;
            if(user != null)
            {
                string token = Guid.NewGuid().ToString();
                user.Token = token;
                //save user
                return user;
            }
            return null;
        }
    }

}
