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
        private GenericRepository<Category> categoryRepository;
        private GenericRepository<QrCode> qrCodeRepository;
        private GenericRepository<SecurityClient> securityClientRepository;
        private GenericRepository<Ticket> ticketRepository;

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

        public IRepository<Category> CategoryRepository
        {
            get
            {
                if (this.categoryRepository == null)
                    categoryRepository = new GenericRepository<Category>(context);

                return categoryRepository;
            }
        }

        public IRepository<QrCode> QrCodeRepository
        {
            get
            {
                if (this.qrCodeRepository == null)
                    qrCodeRepository = new GenericRepository<QrCode>(context);

                return qrCodeRepository;
            }
        }

        public IRepository<SecurityClient> SecurityClientRepository
        {
            get
            {
                if (this.securityClientRepository == null)
                    securityClientRepository = new GenericRepository<SecurityClient>(context);

                return securityClientRepository;
            }
        }

        public IRepository<Ticket> TicketRepository
        {
            get
            {
                if (this.ticketRepository == null)
                    ticketRepository = new GenericRepository<Ticket>(context);

                return ticketRepository;
            }
        }

        public void Save()
        {
            context.SaveChanges();
        }
    }
}
