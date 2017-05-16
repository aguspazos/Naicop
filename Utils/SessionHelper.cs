using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;

namespace Helpers
{
    public class SessionHelper
    {
        public static string USER_SESSION_NAME = "current";

        public static  Authentication  GetCurrentAuthenticated()
        {
            return (Authentication) HttpContext.Current.Session[USER_SESSION_NAME];
        }

        public static void SetCurrentAuthenticated(Authentication authentication)
        {
            HttpContext.Current.Session[USER_SESSION_NAME] = authentication;
        }
    }
}
