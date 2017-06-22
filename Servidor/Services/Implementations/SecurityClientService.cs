using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Entitites;
using Repository;

namespace Services.Implementations
{
    public class SecurityClientService : ISecurityClientService
    {

        private IUnitOfWork unitOfWork;

        public SecurityClientService()
        {
            this.unitOfWork = new UnitOfWork();
        }

        public SecurityClientService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }
        public SecurityClient CreateSecurityClient(SecurityClient securityClient)
        {
            unitOfWork.SecurityClientRepository.Insert(securityClient);
            unitOfWork.Save();
            return securityClient;
        }

        public SecurityClient GetByToken(string token)
        {
            var securityClientEnumerable = unitOfWork.SecurityClientRepository.Get(s => s.Token == token);
            if(securityClientEnumerable.Count() > 0)
            {
                return securityClientEnumerable.First();
            }
            else
            {
                return null;
            }
        }
    }
}
