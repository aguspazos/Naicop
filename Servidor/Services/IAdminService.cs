﻿using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public interface IAdminService
    {
        Admin Login(string email, string password);
        Admin CreateAdmin(Admin admin);
    }
}
