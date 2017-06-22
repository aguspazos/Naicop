using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entitites
{
    public class User : Authentication
    {
        public static  int PASSWORD_MIN_LENGHT = 6;

        public string Token { get; set; }
        public string Name { get; set; }
        public string LastName { get; set; }
        public string Phone { get; set; }
        public string FacebookID { get; set; }
    }
}
