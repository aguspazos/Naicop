using Entitites;
using Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public class AuthenticationService
    {
        public bool CheckPermission(int role,bool all = false)
        {
            if (all) return true;

            Authentication auth = SessionHelper.GetCurrentAuthenticated();
            return auth != null && auth.Role ==role;
        }

        
    }
}
