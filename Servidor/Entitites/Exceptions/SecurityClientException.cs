using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entitites.Exceptions
{
    class SecurityClientException : Exception
    {
        public SecurityClientException(string message):base(message){ }
    }
}
