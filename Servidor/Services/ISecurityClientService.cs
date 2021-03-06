﻿using Entitites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public interface ISecurityClientService
    {
        SecurityClient CreateSecurityClient(SecurityClient securityClient);
        SecurityClient Login(string email, string password);
        SecurityClient GetByToken(string token);
    }
}
