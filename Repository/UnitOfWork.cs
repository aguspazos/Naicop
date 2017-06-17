using DataAcces;
using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repository
{
    public class UnitOfWork : IUnitOfWork
    {
        private Context context;
        private GenericRepository<User> userRepository;
        private GenericRepository<Admin> adminRepository;
        private GenericRepository<ClientUser> clientUserRepository;
        private GenericRepository<Event> eventRepository;

        public UnitOfWork()
        {
            context = new Context();
        }
        public UnitOfWork(Context context)
        {
            this.context = context;
        }

        public IRepository<User> UserRepository
        {
            get
            {
               if (this.userRepository == null)
                {
                    this.userRepository = new GenericRepository<User>(context);
                }
                return userRepository;
            }
        }

        public IRepository<Admin> AdminRepository
        {
            get
            {
                if (this.adminRepository == null)
                {
                    adminRepository = new GenericRepository<Admin>(context);
                }
                return adminRepository;
            }
        }
        public IRepository<ClientUser> ClientUserRepository
        {
            get
            {
                if (this.clientUserRepository == null)
                    clientUserRepository = new GenericRepository<ClientUser>(context);

                return clientUserRepository;

            }
        }
        public IRepository<Event> EventRepository
        {
            get
            {
                if (this.eventRepository == null)
                    eventRepository = new GenericRepository<Event>(context);

                return eventRepository;

            }
        }



        public void Save()
        {
            context.SaveChanges();
        }
    }
}
