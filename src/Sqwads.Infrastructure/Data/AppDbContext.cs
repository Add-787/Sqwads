using Microsoft.EntityFrameworkCore;
using Sqwads.Domain.Players;
using Sqwads.Domain.Squads;

namespace Sqwads.Infrastructure.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions options) : base(options)
    {

    }

    public DbSet<Player> Players => Set<Player>();
    public DbSet<Squad> Squads => Set<Squad>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
    }


}
