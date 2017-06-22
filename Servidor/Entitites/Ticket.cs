using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entitites
{
    public class Ticket
    {

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ID { get; set; }

        public int EventID { get; set; }
        public int UserID { get; set; }
        public string QrImageUrl { get; set; }
        public int Used { get; set; }
        public string Code { get; set; }
        public DateTime UpdatedOn { get; set; }
        public DateTime CreatedOn { get; set; }
        public TicketStatus Status { get; set; }
        public int Deleted { get; set; }

    }
}
