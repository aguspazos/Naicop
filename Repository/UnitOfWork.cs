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

        public IRepository<Stock> StockRepository
        {
            get
            {
                if (this.stockRepository == null)
                {
                    this.stockRepository = new GenericRepository<Stock>(context);
                }
                return stockRepository;
            }
        }

        public IRepository<Price> PriceRepository
        {
            get
            {
                if (this.priceRepository == null)
                {
                    this.priceRepository = new GenericRepository<Price>(context);
                }
                return priceRepository;
            }
        }
        public IRepository<Transaction> TransactionRepository
        {
            get
            {
                if (this.transactionRepository == null)
                {
                    this.transactionRepository = new GenericRepository<Transaction>(context);
                }
                return transactionRepository;
            }
        }

        public IRepository<UserStocks> UserStocksRepository
        {
            get
            {
                if (this.userStocksRepository == null)
                {
                    userStocksRepository = new GenericRepository<UserStocks>(context);
                }
                return userStocksRepository;
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

        public IRepository<News> NewsRepository
        {
            get
            {
                if (this.newsRepository == null)
                {
                    newsRepository = new GenericRepository<News>(context);
                }
                return newsRepository;
            }
        }
        public IRepository<GameRules> GameRulesRepository
        {
            get
            {
                if (this.gameRulesRepository == null)
                {
                    gameRulesRepository = new GenericRepository<GameRules>(context);
                }
                return gameRulesRepository;
            }
        }

        public IRepository<Log> LoggerRepository
        {
            get
            {
                if (this.loggerRepository == null)
                {
                    loggerRepository = new GenericRepository<Log>(context);
                }
                return loggerRepository;
            }
        }
        public void Save()
        {
            context.SaveChanges();
        }
    }
}
