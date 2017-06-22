using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public interface IUserService
    {
        User Login(string email, string password);
        User CreateUser(User user);
        User GetFromToken(string token);
<<<<<<< HEAD
        User FacebookLogin(User user);
=======
        User UpdateUser(string token, User user);
>>>>>>> 706d9be333e2af7f37636f52a731162f39e1da2b
    }
}
