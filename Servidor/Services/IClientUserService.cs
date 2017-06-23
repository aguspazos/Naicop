using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public interface IClientUserService
    {
        ClientUser Login(string email, string password);
        ClientUser CreateClientUser(ClientUser user,string image,string imageName);
    }
}
