﻿using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repository
{
    public interface IUnitOfWork
    {
        IRepository<User> UserRepository { get; }
        IRepository<Admin> AdminRepository { get; }
        IRepository<ClientUser> ClientUserRepository { get; }
        IRepository<Event> EventRepository { get; }
        IRepository<Category> CategoryRepository { get; }
        IRepository<QrCode> QrCodeRepository { get; }
        IRepository<SecurityClient> SecurityClientRepository { get; }
        IRepository<Ticket> TicketRepository { get; }
        void Save();
    }
}
