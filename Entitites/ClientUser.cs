using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entitites
{
    public class ClientUser : Authentication
    {
        public string ImageUrl { get; set; }
        public string Description { get; set; }

        public virtual List<Event> Events { get; set; }
    }
}
