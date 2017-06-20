using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
   public interface IAuthenticationService
    {
        bool CheckPermission(int role,bool all = false);
        void Log(Authentication auth);
    }
}
