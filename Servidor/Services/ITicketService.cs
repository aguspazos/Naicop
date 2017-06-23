using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public interface ITicketService
    {
        Ticket CreateTicket(Ticket ticket);

        Ticket GetByCode(string code);
        Ticket MakePayment(Ticket ticket);
        List<Ticket> GetUpdated(DateTime updatedOn);
        Ticket ScanTicket(Ticket ticket);
        
    }
}
