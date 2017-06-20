using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Entitites;
using Repository;

namespace Services.Implementations
{
    public class TicketService : ITicketService
    {

        private IUnitOfWork unitOfWork;
        
        public TicketService()
        {
            this.unitOfWork = new UnitOfWork();
        }
         
        public Ticket CreateTicket(Ticket ticket)
        {
            unitOfWork.TicketRepository.Insert(ticket);
            unitOfWork.Save();
            return ticket;
        }
    }
}
