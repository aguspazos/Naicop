using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entitites.Exceptions
{
    public class TicketException : Exception
    {
        public TicketException(string message) : base(message) { }
    }
}
