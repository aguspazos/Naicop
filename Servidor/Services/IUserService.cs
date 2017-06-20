using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    interface IUserService
    {
        User Login(string email, string password);
        User CreateUser(User user);
        User GetFromToken(string token);
    }
}
