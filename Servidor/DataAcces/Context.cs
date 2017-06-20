using DataAcces.Migrations;
using Entitites;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAcces
{
    public class Context : DbContext
    {
        public virtual DbSet<User> Users { get; set; }
        public virtual DbSet<Admin> Admins { get; set; }
        public virtual DbSet<Event> Events { get; set; }
        public virtual DbSet<Category> Categories { get; set; }
        public virtual DbSet<Ticket> Tickets { get; set; }
        public virtual DbSet<QrCode> QRCodes { get; set; }
        public virtual DbSet<SecurityClient> SecurityClient { get; set; }

        public Context()
             : base("Context")
        {
            Database.SetInitializer(new MigrateDatabaseToLatestVersion<Context,
                Configuration>("Context"));

        }
        
    }
}
