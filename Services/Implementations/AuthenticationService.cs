using Entitites;
using Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services.Implementations
{
    public class AuthenticationService : IAuthenticationService
    {

        public bool CheckPermission(int role,bool all = false)
        {
            if (all) return true;

            Authentication auth = SessionHelper.GetCurrentAuthenticated();
            return auth != null && auth.Role ==role;
        }

        public void Log(Authentication auth)
        {
            SessionHelper.SetCurrentAuthenticated(auth);
        }
    }
}
