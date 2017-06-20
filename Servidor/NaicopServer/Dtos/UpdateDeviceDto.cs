using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace NaicopServer.Dtos
{
    public class UpdateDeviceDto 
    {
        public List<Event> Events { get; set; }
        public List<Category> Categories { get; set;}
        public List<Ticket> Tickets { get; set; }
    }
}