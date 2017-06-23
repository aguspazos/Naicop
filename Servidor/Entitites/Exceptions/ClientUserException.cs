using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entitites.Exceptions
{
    public class ClientUserException : Exception
    {
        public ClientUserException(string message) : base(message) { }
    }
}
