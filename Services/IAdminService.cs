﻿using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    interface IAdminService
    {
        Admin Login(string email, string password);
    }
}