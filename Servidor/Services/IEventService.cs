using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public interface IEventService
    {
        Event CreateEvent(Event newEvent,string imgStr,string imgName);
        List<Event> GetAll();
    }
}
